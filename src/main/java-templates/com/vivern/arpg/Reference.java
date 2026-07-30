package {{ package }};

/**
 * Reference storage class, you can change at will
 */
public class Reference {
    private Reference() {}

    public static final String MOD_ID = "{{ mod_id }}";
    public static final String MOD_NAME = "{{ mod_name }}";
    public static final String VERSION = "{{ mod_version }}";
    public static final String DEPENDENCIES = "";
    public static final String MOD_PACKAGE = "{{ package }}";
    public static final String CLIENT_PROXY_CLASS = MOD_PACKAGE + ".proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = MOD_PACKAGE + ".proxy.CommonProxy";

}