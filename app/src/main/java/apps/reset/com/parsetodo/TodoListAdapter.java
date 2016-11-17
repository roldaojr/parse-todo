package apps.reset.com.parsetodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class TodoListAdapter extends ParseQueryAdapter<TodoItem> {
    TodoListActivity activity;

    public TodoListAdapter(Context context) {
        super(context, "Todo");
        activity = (TodoListActivity) context;
    }

    @Override
    public View getItemView(TodoItem object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.todo_item_layout, null);
        }
        super.getItemView(object, v, parent);
        TextView text = (TextView)  v.findViewById(R.id.todo_list_item_text);
        text.setText(object.getTexto());
        text.setTag(object);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.editarItem((TodoItem) v.getTag());
            }
        });
        text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.apagarItem((TodoItem) v.getTag());
                return true;
            }
        });
        CheckBox checkbox = (CheckBox) v.findViewById(R.id.todo_list_item_checkbox);
        checkbox.setTag(object);
        checkbox.setChecked(object.isFeito());
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkbox = (CheckBox) v;
                TodoItem item = (TodoItem) v.getTag();
                item.setFeito(checkbox.isChecked());
                item.saveEventually();
            }
        });
        return v;
    }
}
