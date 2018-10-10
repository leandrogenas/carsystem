package atox;

import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Configs {
    private static final String CAM_INI = "carsystem.ini";
    private static Configs instancia = null;

    private Wini ini;

    public static Configs getConfigs() throws Exception{
        if(instancia == null)
            instancia = new Configs(new File(CAM_INI));

        return instancia;

    }

    public Configs(File arqIni) throws IOException {
        this.ini = new Wini(arqIni);
    }

    public Map<String, String> getSecao(String secao){
        return ini.get(secao);
    }

    public String getValor(String secao, String chave){
        Ini.Section sect = ini.get(secao);

        return sect.get(chave);
    }

}
