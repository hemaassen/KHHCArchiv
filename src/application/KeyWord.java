package application;

public class KeyWord {
	private Integer id;
	private String keyword;  	//Schlüsselwort
	private String path;		//ein eventuell abweichender Pfad vom Schlüsselwort
								//in Version 1=keyword
	private Integer parent;		//die Id des übergeordneten Schlüsselwortes
	private Integer level;		//die dem Schlüsselwort zugeordnete Ebene
	
	//Getter und Setter
	
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
	
	//Konstruktoren
	public KeyWord() {
		// TODO Auto-generated constructor stub
	}

	public KeyWord(Integer id, String keyword, String path, Integer parent, Integer level) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.path = path;
		this.parent = parent;
		this.level = level;
	}

	@Override
	public String toString() {
		return "KeyWord [id=" + id + ", keyword=" + keyword + ", path=" + path + ", parent=" + parent + ", level="
				+ level + "]";
	}
	
}
