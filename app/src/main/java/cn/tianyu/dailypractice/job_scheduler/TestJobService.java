package cn.tianyu.dailypractice.job_scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.widget.Toast;

public class TestJobService extends JobService {
    private JobParameters mParameters;

    @Override
    public boolean onStartJob(JobParameters params) {
        mParameters = params;
        new MyTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private class MyTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(TestJobService.this, "Job finished", Toast.LENGTH_SHORT).show();
            jobFinished(mParameters, false);
            super.onPostExecute(aVoid);
        }
    };
}
