public class Test {
    public void out(){
        new IA(){
            @Override
            public void show() {
                System.out.println("new IA " + a);
                System.out.println(this.getClass());
            }
        }.show();
    }

}
interface IA{
    int a = 11;
    void show();
}