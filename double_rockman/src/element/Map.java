package element;

import org.jbox2d.dynamics.World;

/**
 * 
 * マップの動作を行う
 * 
 * 初期化
 * 	マップの構成要素をjbox2dに設定をする
 * 
 * マップの管理
 * 	マップの中で何かアクションがあった時に対処をする
 * 		マップの更新など 
 * 
 * マップに追加
 * 	マップに対して、キャラクタの追加を行う。
 * 
 * @author ultra-tkymx
 *
 */

public class Map extends Element {
		
	/**
	 * 
	 * jbox2dの世界の情報が入っている
	 * 
	 */
	//世界の情報
	private World gWorld = null;
	
	
	/**
	 * 
	 * 初期化ではマップの生成を行う。
	 * 
	 */
	public Map() {		
		super();
		
		
		
	}
		
	@Override
	public void update() {
		super.update();
		
		
		
	}
	
}
