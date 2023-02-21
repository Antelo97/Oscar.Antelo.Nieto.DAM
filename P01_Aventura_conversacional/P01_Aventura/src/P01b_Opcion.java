public class P01b_Opcion {

    private int id;
    private String text;
    private int result;

    public P01b_Opcion(int id, String text, int result) {
        this.id = id;
        this.text = text;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        String str = "\n  ∎ id: " + id + ", texto: " + text + ", resultado: " + result;
        return str;
    }
}
