package littlemylyn.views;



public class Node {
	private Object data;
	private Node[] sons;
	private Node father;
	
	Node(Object data)
	{
		this.data = data;
	}
	
	void set(Node father,Node[] sons)
	{
		this.father = father;
		this.sons = sons;
	}
	public Object getData() {
		return data;
	}
	public Node getFather(){
		return father;
	}
	public Node[] getSons(){
		return sons;
	}
	
	
	
}
