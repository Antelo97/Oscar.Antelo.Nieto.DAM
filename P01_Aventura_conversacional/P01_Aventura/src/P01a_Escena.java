import java.util.ArrayList;

public class P01a_Escena {
    private int code;
    private String text;
    private ArrayList<P01b_Opcion> options;

    public P01a_Escena() {
    }

    public P01a_Escena(int code, String text, ArrayList<P01b_Opcion> options) {
        this.code = code;
        this.text = text;
        this.options = options;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<P01b_Opcion> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<P01b_Opcion> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        String str = "\nESCENA\n● Código: " + code + "\n● Texto: " + text + "\n● Opciones: " + options.toString() + "\n";
        return str;
    }
}

