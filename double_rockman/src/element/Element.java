package element;

import java.awt.Point;
import java.util.ArrayList;

public class Element {
	
	/**
	 * 
	 * elementの用を再帰的に動作できるようにする。
	 */
	ArrayList<Element> elements;
	
	/**
	 * 
	 * Elementの位置を管理しておく
	 *  
	 */
	Point position;
	
	/**
	 * 
	 * 初期化を行う
	 * 
	 */
	public Element() {

		elements = new ArrayList<Element>();
	
	}
	
	/***
	 * 
	 * 基本的な動作を行う
	 * 
	 * ここに書かれた際に、下の動作も同時に行う事ができる
	 * 
	 */
	public void update()
	{		
		for(Element e : elements)
		{
			e.update();
		}
	}
	
	/**
	 * エレメントの追加を行う
	 * 
	 * @param Element e 追加対象のElement
	 */
	public void add( Element e )
	{
		elements.add(e);
	}

}
