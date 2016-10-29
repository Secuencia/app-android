package moviles.isaacs.com.isaacs.modules.Contents;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sfrsebastian on 10/28/16.
 */

public class ViewWrapper extends RecyclerView.ViewHolder {

    private View view;

    public ViewWrapper(View itemView) {
        super(itemView);
        view = itemView;
    }

    public View getView(){
        return view;
    }
}