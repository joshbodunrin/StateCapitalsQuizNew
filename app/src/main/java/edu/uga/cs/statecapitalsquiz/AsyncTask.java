package edu.uga.cs.statecapitalsquiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;

public abstract class AsyncTask<Param,Result> {

    // internal method to execute task in background
    private void executeInBackground(Param... params) {

        // get excutor service that will run task in background, has its own thread
        // different from main so does not interfer with main UI
        ExecutorService executor = Executors.newSingleThreadExecutor();


        // The lambda expression below effectively creates an anonymous
        // Runnable class that will execute the method's body (doInBackground)
        // in the executor service, which uses a different thread than the main UI thread.
        // Once the result is obtained, the Runnable class will add another Runnable
        // with the call to onPostExecute with the Result argument to be added to the
        // main UI thread.  The main UI thread will update the UI accordingly.
        // Since the Runnable below will execute in a different thread, the main UI thread
        // will not be blocked.
        executor.execute(() -> {

            // Run method body (doInBackground)
            Result result = doInBackground(params);

            // Pass result to main UI thread.
            // Get looper of UI thread(main UI dispachter's loop).
            // Looper is message queue within Android OS
            Looper looper = Looper.getMainLooper();

            // Create handler using main UI looper
            // Handler used to interact with a Looper
            // like posting messages on main Looper's queue
            Handler handler = new Handler(looper);

            // post the processing of result of doInBackground method on main UI
            // thread's looper
            handler.post( new Runnable() {
                @Override
                // handle methor result in main UI thread
                public  void run() {
                    onPostExecute(result);
                }
            });
        });
    }
    public void execute(Param... arguments) {executeInBackground(arguments);}

    protected abstract Result doInBackground(Param... arguments);

    protected abstract void onPostExecute(Result result);
}
