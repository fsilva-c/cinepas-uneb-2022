import model.Cliente;
import model.ingresso.*;
import model.datahora.AdapterDataHora;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        MaquinaIngresso maquinaIngresso = MaquinaIngresso.getInstance();
        // teste prototype pattern...
        Cliente cliente = maquinaIngresso.RegistrarCliente("cpf", "nome");
        Ingresso ingresso = maquinaIngresso.ComprarIngresso(21.99f, cliente);
        Ingresso outroIngresso = maquinaIngresso.ClonarIngresso(ingresso);
        // Cliente cliente = new Cliente("cpf", "nome");
        // Ingresso ingresso = new Ingresso(21.99f, cliente);
        // Ingresso outroIngresso = (Ingresso) ingresso.clonar();

        System.out.println("Ingresso: " + ingresso.toString());
        System.out.println("outroIngresso: " + outroIngresso.toString());

        // teste adapter...
        AdapterDataHora datahora = new AdapterDataHora("16/06/2022 23:04:00");
        System.out.println(datahora.hora());
        System.out.println(datahora.data());
        System.out.println(datahora.datahora());
        System.out.println(datahora.somarMinutos(78));

        AdapterDataHora datahora2 = new AdapterDataHora("16/06/2022 23:04:00");
        AdapterDataHora datahora3 = new AdapterDataHora(datahora.somarMinutos(78));
        System.out.println(datahora3.maiorQue(datahora2.datahora()));

        // teste decorator...
        IngressoBase ing2 = new IngressoMeia(new IngressoEspecial(new Ingresso(34.f, cliente)));
        System.out.println(ing2.calcValor());

        // teste ponteiro objeto...
        Cliente cliente2 = cliente;
        System.out.println(cliente2.getCpf());
        cliente2.setCpf("084");
        System.out.println(cliente.getCpf());

    }
}
