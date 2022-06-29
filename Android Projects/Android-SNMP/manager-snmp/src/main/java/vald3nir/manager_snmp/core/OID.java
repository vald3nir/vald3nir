package vald3nir.manager_snmp.core;

public class OID {

    private final String code;
    private final String description;

    public OID(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getOID() {
        return code;
    }

    @Override
    public String toString() {
        return getDescription() + " - " + getOID();
    }

}
