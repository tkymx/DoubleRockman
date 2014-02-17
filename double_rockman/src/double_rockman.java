import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * 
 * ダブルロックマン
 * 
 * ロックマンであるが、強制スクロールするゴールに向かって
 * お互いを邪魔しながら、ゴールし総合得点が高いほうが価値
 * 
 * 得点には、残りHPゴールタイム、敵を倒した数、コインをゲットした数等がある。
 * 
 * @author ultra-tkymx
 *
 */

public class double_rockman extends JPanel {
	
  private static final long serialVersionUID = 1L;

  
	public static final int FPS = 60;
	
	//世界の情報
	private World gWorld = null;

	//ループの情報
	private boolean _loop = true;
	
	// 表示情報を管理するクラス
	class Rect {
		public float _width = 0;
		public float _height = 0;
		Rect(float w, float h) {
			_width = w;
			_height = h;
		}
	}	
	
	//物体クラス
	class RockPhysics implements Runnable
	{		  
		
		private void CreateObj( World _world )
		{
			float w = 8;
			float h = 2;
			
			BodyDef bd = new BodyDef();
			bd.type = BodyType.DYNAMIC;
			bd.position.set(64,0);
			bd.angle = 0;

			Body body = _world.createBody(bd);

			PolygonShape ps = new PolygonShape();
			ps.setAsBox(w / 2, h / 2);

			FixtureDef fd = new FixtureDef();
			fd.shape = ps;
			fd.density = 0.5f;   // 密度
			fd.friction = 0.1f;   // 摩擦
			fd.restitution = 0.1f;   // 反発
			body.createFixture(fd);

			body.setUserData(new Rect(w, h));			
		}
		
		@Override
		public void run() {
			long pt = System.nanoTime();
			long ct = 0;
			
			CreateObj(gWorld);
			
			
			try {
				while(_loop) {
					
					// 実際に経過した時間だけ処理を進める
					ct = System.nanoTime();
					long course = (ct - pt);
					
					if( gWorld != null )
					{
						//世界の更新
						gWorld.step(course / 1000f / 1000f / 1000f, 8, 8);

						//再描画
						repaint();	
					}
					
					//時間の更新
					pt = ct;
					
					Thread.sleep(1000 / FPS);
					
				}				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
		}	  	
	}
	
	//コンストラクタ
	public double_rockman() {
		  			
	    	    
	}
  
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;		
		
		// 回転を戻す
		AffineTransform ati = new AffineTransform();
		g2.setTransform(ati);
	
		//背景の描画
		Rectangle backrect = new Rectangle();
		backrect.setRect(0,0,getSize().width,getSize().height);
		g2.setColor(Color.WHITE);
		g2.fill(backrect);
		
		if(gWorld==null)return;
		
		//倍率
		final float scale = 10;
			
		for (Body body = gWorld.getBodyList(); body != null; body = body.getNext()) {
			try {
				
				Vec2 position = body.getPosition();
				Rect obj = (Rect)body.getUserData();

				// オブジェクトの左上座標(ピクセル)
				int oxb = (int)((position.x*scale - obj._width*scale / 2.0f));
				int oyb = (int)((position.y*scale - obj._height*scale / 2.0f));

				// オブジェクトのサイズ(ピクセル)
				int ow = (int)(obj._width*scale);
				int oh = (int)(obj._height*scale);

				AffineTransform at = new AffineTransform();
				at.setToRotation(body.getAngle(), oxb + ow/2, oyb + oh/2);				
				g2.setTransform(at);
				
				Rectangle rect = new Rectangle( oxb, oyb, ow, oh );
				g2.setColor(Color.BLACK);
				g2.draw(rect);
				
			} catch(RuntimeException e) {
			}
		}		
	}
	 
	public void init()
	{		
		
		// jbox2dの初期化//////////////////////
		Vec2 gravity = new Vec2(0, 9.8f);
		gWorld = new World(gravity);

		// 地面の定義///////////////////////////
		BodyDef bd = new BodyDef();
		bd.position.set(64,60);
		bd.angle = (float)Math.PI / 180 * 15;
		
		//createBodyをした段階でworldに登録される
		Body body = gWorld.createBody(bd);

		PolygonShape ps = new PolygonShape();
		float w = 40f;
		float h = 5f;
		ps.setAsBox(w / 2, h / 2);

		body.createFixture(ps, 0f);
		body.setUserData(new Rect(w, h));
		
		// 描画スレッド開始///////////////////////
		new Thread(new RockPhysics()).start();				
	}
	
	public static void main(String[] args) {

		JFrame f = new JFrame();
		f.setTitle("ダブルロックマン");
		f.setSize(1280,720);
		f.setLocation(200, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		//パネルの設定
		double_rockman panel = new double_rockman();		
		panel.init();
		
		f.add(panel);
		
		f.setVisible(true);
		
		
				
	}
  
}