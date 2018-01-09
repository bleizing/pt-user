package bleizing.punyatemenuser;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemTwoFragment extends Fragment {
    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_two,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);
        gridView.setAdapter(new gridAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
        return view;

        //return inflater.inflate(R.layout.fragment_item_two, container, false);
    }
}
