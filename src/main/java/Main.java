import pocs.Tarefa;

public class Main {

    public static void main(String[] args) {
        multiThread();
    }

    // poc multiThread
    private static void multiThread() {
        //cria três tarefas
        Tarefa t1 = new Tarefa(0, 1000);
        t1.setName("Tarefa1");
        Tarefa t2 = new Tarefa(1001, 2000);
        t2.setName("Tarefa2");
        Tarefa t3 = new Tarefa(2001, 3000);
        t3.setName("Tarefa3");
        Tarefa t4 = new Tarefa(3001, 4000);
        t4.setName("Tarefa4");

        //inicia a execução paralela das três tarefas, iniciando três novas threads no programa
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        //aguarda a finalização das tarefas
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        //Exibimos o somatório dos totalizadores de cada Thread
        System.out.println("Total: " + (t1.getTotal() + t2.getTotal() + t3.getTotal() + t4.getTotal()));
    }
}
