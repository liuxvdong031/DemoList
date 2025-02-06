package com.xvdong.rebot;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xvDong on 2025/2/6.
 */
public class TwoCameraActivity extends Activity  {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private TextureView textureView1, textureView2;
    private CameraDevice cameraDevice1, cameraDevice2;
    private CameraCaptureSession captureSession1, captureSession2;
    private CaptureRequest.Builder captureRequestBuilder1, captureRequestBuilder2;
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;
    private Size previewSize1, previewSize2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_camera);

        textureView1 = findViewById(R.id.textureView1);
        textureView2 = findViewById(R.id.textureView2);

        textureView1.setSurfaceTextureListener(textureListener1);
        textureView2.setSurfaceTextureListener(textureListener2);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }else {
            checkCameraCount();
        }
    }


    private void checkCameraCount() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            Log.d("TAG", "checkCameraCount: " + cameraIds.length);
            if (cameraIds.length < 2) {
                // 设备不支持双摄像头，给出提示
                Toast.makeText(this, "设备不支持双摄像头", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "支持双摄像头", Toast.LENGTH_SHORT).show();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startBackgroundThread();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final TextureView.SurfaceTextureListener textureListener1 = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            openCamera(0, textureView1);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            // 处理纹理视图大小变化
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
            // 处理纹理视图更新
        }
    };

    private final TextureView.SurfaceTextureListener textureListener2 = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            openCamera(1, textureView2);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            // 处理纹理视图大小变化
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
            // 处理纹理视图更新
        }
    };

    private final CameraDevice.StateCallback stateCallback1 = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice1 = camera;
            createCaptureSession(cameraDevice1, textureView1);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            cameraDevice1 = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            cameraDevice1 = null;
        }
    };

    private final CameraDevice.StateCallback stateCallback2 = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice2 = camera;
            createCaptureSession(cameraDevice2, textureView2);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            cameraDevice2 = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            cameraDevice2 = null;
        }
    };

    private void openCamera(int cameraId, TextureView textureView) {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraIdStr = cameraManager.getCameraIdList()[cameraId];
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraIdStr);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map != null) {
                if (cameraId == 0) {
                    previewSize1 = map.getOutputSizes(SurfaceTexture.class)[0];
                } else {
                    previewSize2 = map.getOutputSizes(SurfaceTexture.class)[0];
                }
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (cameraId == 0) {
                cameraManager.openCamera(cameraIdStr, stateCallback1, backgroundHandler);
            } else {
                cameraManager.openCamera(cameraIdStr, stateCallback2, backgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCaptureSession(CameraDevice cameraDevice, TextureView textureView) {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            if (texture == null) {
                return;
            }
            if (cameraDevice == cameraDevice1) {
                texture.setDefaultBufferSize(previewSize1.getWidth(), previewSize1.getHeight());
            } else {
                texture.setDefaultBufferSize(previewSize2.getWidth(), previewSize2.getHeight());
            }
            Surface surface = new Surface(texture);

            if (cameraDevice == cameraDevice1) {
                captureRequestBuilder1 = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                captureRequestBuilder1.addTarget(surface);
            } else {
                captureRequestBuilder2 = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                captureRequestBuilder2.addTarget(surface);
            }

            List<Surface> outputSurfaces = new ArrayList<>();
            outputSurfaces.add(surface);

            if (cameraDevice == cameraDevice1) {
                cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {
                        if (cameraDevice1 == null) {
                            return;
                        }
                        captureSession1 = session;
                        try {
                            captureRequestBuilder1.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                            CaptureRequest previewRequest = captureRequestBuilder1.build();
                            captureSession1.setRepeatingRequest(previewRequest, null, backgroundHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                        Toast.makeText(TwoCameraActivity.this, "Camera session configuration failed", Toast.LENGTH_SHORT).show();
                    }
                }, backgroundHandler);
            } else {
                cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {
                        if (cameraDevice2 == null) {
                            return;
                        }
                        captureSession2 = session;
                        try {
                            captureRequestBuilder2.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                            CaptureRequest previewRequest = captureRequestBuilder2.build();
                            captureSession2.setRepeatingRequest(previewRequest, null, backgroundHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                        Toast.makeText(TwoCameraActivity.this, "Camera session configuration failed", Toast.LENGTH_SHORT).show();
                    }
                }, backgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        backgroundThread = new HandlerThread("CameraBackground");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        backgroundThread.quitSafely();
        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if (textureView1.isAvailable()) {
            openCamera(0, textureView1);
        } else {
            textureView1.setSurfaceTextureListener(textureListener1);
        }
        if (textureView2.isAvailable()) {
            openCamera(1, textureView2);
        } else {
            textureView2.setSurfaceTextureListener(textureListener2);
        }
    }

    @Override
    protected void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    private void closeCamera() {
        if (captureSession1 != null) {
            captureSession1.close();
            captureSession1 = null;
        }
        if (cameraDevice1 != null) {
            cameraDevice1.close();
            cameraDevice1 = null;
        }
        if (captureSession2 != null) {
            captureSession2.close();
            captureSession2 = null;
        }
        if (cameraDevice2 != null) {
            cameraDevice2.close();
            cameraDevice2 = null;
        }
    }
}
