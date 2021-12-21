package be.scryper.sos.async;

import android.os.AsyncTask;

public class InitTask extends AsyncTask<Integer, Void, String> {

    public AsyncResponse delegate = null;

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    public InitTask(AsyncResponse delegate) {
        this.delegate = delegate;

    }

    @Override
    protected String doInBackground(Integer... lists) {
        
        return null;
    }
}
