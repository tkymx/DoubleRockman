package element;

import java.awt.Point;
import java.util.ArrayList;

public class Element {
	
	/**
	 * 
	 * element�̗p���ċA�I�ɓ���ł���悤�ɂ���B
	 */
	ArrayList<Element> elements;
	
	/**
	 * 
	 * Element�̈ʒu���Ǘ����Ă���
	 *  
	 */
	Point position;
	
	/**
	 * 
	 * ���������s��
	 * 
	 */
	public Element() {

		elements = new ArrayList<Element>();
	
	}
	
	/***
	 * 
	 * ��{�I�ȓ�����s��
	 * 
	 * �����ɏ����ꂽ�ۂɁA���̓���������ɍs�������ł���
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
	 * �G�������g�̒ǉ����s��
	 * 
	 * @param Element e �ǉ��Ώۂ�Element
	 */
	public void add( Element e )
	{
		elements.add(e);
	}

}
