package vald3nir.manager_snmp.core;

public class MIBs {

    public static final Integer PORT_SNMP = 1721;
    public static final String VERSION_SNMP = "1";
    public static final String COMMUNITY = "ANDROID";

    // =============================================================================================

    public static final String SEPARATOR = ",";

    // =============================================================================================

    public static final String GET_REQUEST = "0";
    public static final String GET_NEXT_REQUEST = "1";
    public static final String GET_RESPONSE = "2";
    public static final String SET_REQUEST = "3";
    public static final String TRAP = "4";

    // LISTAS DE MIBS - http://www.alvestrand.no/objectid/1.3.6.1.4.1.html
    // =============================================================================================

    private static final String MIB_PRIVATE = "1.3.6.1.4.1.12619.";

    public static final String MIB_DEVICE_MODEL = MIB_PRIVATE + "1.1.1";
    public static final String MIB_VERSION_SYSTEM_OPERATION = MIB_PRIVATE + "1.1.2";
    public static final String MIB_STATUS_BATTERY = MIB_PRIVATE + "1.1.3";
    public static final String MIB_NIVEL_BATTERY = MIB_PRIVATE + "1.1.4";
    public static final String MIB_STATUS_GPS = MIB_PRIVATE + "1.1.5";
    public static final String MIB_STATUS_BLUETOOTH = MIB_PRIVATE + "1.1.6";
    public static final String MIB_STATUS_WIFI = MIB_PRIVATE + "1.1.7";
    public static final String MIB_TRAP_ANDROID = MIB_PRIVATE + "1.2.1";

    // TIPO DE STATUS DE ERRO PARA GET, GET NEXT REQUEST E GET RESPONSE
    // =============================================================================================

    public static final String NO_ERROR = "0";
    /* Indica que não houve qualquer tipo de erro no processamento da mensagem */

    public static final String NO_SUCH_NAME = "2";
    /*MIB não encontrada. Neste caso, o valor do campo error-index indica em qual variável da lista
     ocorreu o problema*/

    public static final String GEN_ERR = "5";
    /*Se o valor de uma determinada variável da lista não puder ser identificado.
    Neste caso, o valor do campo errorindex indica em qual variável da lista ocorreu o problema*/


}
