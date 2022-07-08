package model.datahora;

public interface IDataHora {
    // sess = 10:02 film = 70min
    // addMin(sess,film) -> 11:17
    // addSess(11:10)
    // addSess(sess,sala) -> sess:sala -> maiorQue(finalSess,Sess) -> 11:17 > 11:10
    // -> true
    public String datahora();

    public String data();

    public String hora();

    public String sqlDateTime();

    public String somarMinutos(long minutos);

    public boolean maiorQue(String datahora2);

    public boolean isFDS();

}
