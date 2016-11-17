package apps.reset.com.parsetodo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class TodoListActivity extends AppCompatActivity {
    ListView todoListView;
    TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoListView = (ListView) findViewById(R.id.todo_list_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarItem();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        todoListAdapter = new TodoListAdapter(this);
        todoListView.setAdapter(todoListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            todoListAdapter.loadObjects();
            todoListAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    public void adicionarItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setTitle("Add todo").setView(input)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TodoItem it = new TodoItem();
                        it.setTexto(input.getText().toString());
                        it.saveEventually(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                todoListAdapter.loadObjects();
                                todoListAdapter.notifyDataSetChanged();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public void editarItem(final TodoItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(item.getTexto());
        builder.setTitle("Edit todo").setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item.setTexto(input.getText().toString());
                        item.saveEventually(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                todoListAdapter.loadObjects();
                                todoListAdapter.notifyDataSetChanged();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public void apagarItem(final TodoItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete TODO item")
                .setMessage("Delete this todo item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item.deleteEventually(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                todoListAdapter.loadObjects();
                                todoListAdapter.notifyDataSetChanged();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

}
