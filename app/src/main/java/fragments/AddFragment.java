package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.fs.com.futuresociety.MainActivity;

/**
 * Created by gournandi on 4/05/16.
 */
public class AddFragment {

    public static void AddFragmentActivityStack(Context context, Bundle b, Class<? extends Fragment> fragmentClass) {

        if (context instanceof MainActivity)
            ((MainActivity) context).addFragmentToStack(Fragment.instantiate(context,
                    fragmentClass.getName(), b));



    }

}
