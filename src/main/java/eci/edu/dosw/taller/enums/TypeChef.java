package eci.edu.dosw.taller.enums;

/**
 * Clase numerica que define el rol de cada chef
 */
public enum TypeChef {
    PARTICIPANTE("Participante"),
    JURADO("Jurado"),
    TELEVIDENTE("Televidente");
    private String displayName;

    TypeChef(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public static TypeChef fromDisplayName(String name) {
        if (name == null) return null;
        for (TypeChef s : TypeChef.values()) {
            if (s.getDisplayName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}
