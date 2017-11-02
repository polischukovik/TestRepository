package command;

import java.util.HashMap;
import java.util.Map;

public enum CommandsEnum {
	LOGIN_COMMAND("login", LoginCommand.class),
	REGISTER_COMMAND("register", RegisterCommand.class),
	LOGOUT_COMMAND("logout", LogoutCommand.class);	

    private final String code;
    private final Class<? extends ServerCommand> clazz;
    private static Map<String, Class<? extends ServerCommand>> mMap;

    private CommandsEnum(String code, Class<? extends ServerCommand> clazz) {
		this.code = code;
		this.clazz = clazz;
	}
    
    public String getCode() {
        return code;
    }

    public Class<? extends ServerCommand> getClazz() {
        return clazz;
    }

    public static Class<? extends ServerCommand> getClassByCode(String code) {
        if (mMap == null) {
            initializeMapping();
        }
        if (mMap.containsKey(code)) {
            return mMap.get(code);
        }
        return null;
    }

    private static void initializeMapping() {
        mMap = new HashMap<String, Class<? extends ServerCommand>>();
        for (CommandsEnum s : CommandsEnum.values()) {
            mMap.put(s.code, s.clazz);
        }
    }
}
