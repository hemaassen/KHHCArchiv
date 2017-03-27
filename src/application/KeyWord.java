package application;

public class KeyWord implements Comparable<KeyWord> {
    private Integer id;
    private String keyword; // Schlüsselwort
    private String path; // ein eventuell abweichender Pfad vom Schlüsselwort
    // in Version 1=keyword
    private Integer parent; // die Id des übergeordneten Schlüsselwortes
    private int level; // die dem Schlüsselwort zugeordnete Ebene

    // Getter und Setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    // Konstruktoren
    public KeyWord() {
        // TODO Auto-generated constructor stub
    }

    /**
     * KeyWord Konstruktor.
     * @param id Laufende Numme
     * @param keyword Vom Nutzer festgelegtes Schlüsselwort z.B. Versicherung
     * @param path Hier werden Dateien des Schlüsselwortes abgelegt
     * @param parent Das KeyWord gehört zu diesem KeyWord
     * @param level Auf welcher Ebene liegt dieses Keyword
     */
    public KeyWord(Integer id, String keyword, String path, Integer parent, int level) {
        super();
        this.id = id;
        this.keyword = keyword;
        this.path = path;
        this.parent = parent;
        this.level = level;
    }

    /**
     * in der ObserverableList wird bei der Anzeige in der Combobox auf die String-repräsentation
     * der List-Elemente zugegriffen. Darum hier eine absolute Kurzvariante.
     * 
     * @author Kerstin
     */
    @Override
    public String toString() {
        return keyword;
    }

    /**
     * die "natürlich Ordnung" der Klasse KeyWord wird über das Attribut keywort geregelt. Hier wird
     * dann die alphabetische Reihenfolge genutzt. Ausnahme ist "Neuer Eintrag.." der immer am Ende
     * der Liste steht eine Unterscheidung nach Groß-und Kleinbuchstaben wird nicht gemacht
     * 
     * @author Kerstin
     */
    @Override
    public int compareTo(KeyWord k) {
        int result = 0;
        if (keyword.equals("Neuer Eintrag..")) {
            result = 1;
        } else if (k.getKeyword().equals("Neuer Eintrag..")) {
            result = -1;
        } else {
            result = keyword.toLowerCase().compareTo(k.getKeyword().toLowerCase());
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
        result = prime * result + level;
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        KeyWord other = (KeyWord) obj;
        if (keyword == null) {
            if (other.keyword != null) {
                return false;
            }
        } else if (!keyword.equals(other.keyword)) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!parent.equals(other.parent)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

}
