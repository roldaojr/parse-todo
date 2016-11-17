package apps.reset.com.parsetodo;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Todo")
public class TodoItem extends ParseObject {
    public boolean isFeito() {
        return getBoolean("feito");
    }

    public void setFeito(boolean feito) {
        put("feito", feito);
    }

    public String getTexto() {
        return getString("texto");
    }

    public void setTexto(String texto) {
        put("texto", texto);
    }
}
