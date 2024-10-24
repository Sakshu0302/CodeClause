import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FaceAuthenticationSystem {

    static { 
        // Load OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
    }

    public static void main(String[] args) {

        // Load the face detection classifier (Haar Cascade)
        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");

        // Open the default webcam (0 is for the default camera)
        VideoCapture capture = new VideoCapture(0);  
        if (!capture.isOpened()) {
            System.out.println("Error: Cannot open the camera.");
            return;
        }

        Mat frame = new Mat();  // To hold frames from the camera
        Mat grayFrame = new Mat();  // To hold grayscale frames for face detection

        while (true) {
            // Capture a frame from the camera
            capture.read(frame);
            if (frame.empty()) {
                System.out.println("Error: No frame captured.");
                break;
            }

            // Convert the frame to grayscale
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(grayFrame, grayFrame);  // Improve contrast

            // Detect faces in the grayscale frame
            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(grayFrame, faces, 1.1, 5, 0, new Size(30, 30), new Size());

            // Draw rectangles around detected faces
            for (Rect face : faces.toArray()) {
                Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 3);
            }

            // Save the frame with the detected face rectangles (for verification)
            Imgcodecs.imwrite("output.jpg", frame);

            // Optionally: Display the image with face rectangles (extend to use with GUI)
            if (!displayImage(frame)) {
                break;
            }
        }

        // Release the camera
        capture.release();
    }

    // Method to display the image with face detection (optional: implement in a GUI)
    public static boolean displayImage(Mat image) {
        // Placeholder: This is where you would implement a GUI-based image display using JFrame, etc.
        // For now, this can save the output and return true to keep the loop running.
        return true; 
    }
}
