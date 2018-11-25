package atox.utils;

import atox.model.Cliente;

public final class Mock {

    public static Cliente mockCliente(){
        return new Cliente(
                "12345678909",
                "Aznezio Ferreira",
                "Rua dos aznezio, 3231",
                "(11) 1234-5678",
                "(11) 12345-6789",
                "zezniao@ig.com.br"
        );
    }

}
