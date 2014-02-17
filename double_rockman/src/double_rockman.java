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
 * �_�u�����b�N�}��
 * 
 * ���b�N�}���ł��邪�A�����X�N���[������S�[���Ɍ�������
 * ���݂����ז����Ȃ���A�S�[�����������_�������ق������l
 * 
 * ���_�ɂ́A�c��HP�S�[���^�C���A�G��|�������A�R�C�����Q�b�g��������������B
 * 
 * @author ultra-tkymx
 *
 */

public class double_rockman extends JPanel {
	
  private static final long serialVersionUID = 1L;

  
	public static final int FPS = 60;
	
	//���E�̏��
	private World gWorld = null;

	//���[�v�̏��
	private boolean _loop = true;
	
	// �\�������Ǘ�����N���X
	class Rect {
		public float _width = 0;
		public float _height = 0;
		Rect(float w, float h) {
			_width = w;
			_height = h;
		}
	}	
	
	//���̃N���X
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
			fd.density = 0.5f;   // ���x
			fd.friction = 0.1f;   // ���C
			fd.restitution = 0.1f;   // ����
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
					
					// ���ۂɌo�߂������Ԃ���������i�߂�
					ct = System.nanoTime();
					long course = (ct - pt);
					
					if( gWorld != null )
					{
						//���E�̍X�V
						gWorld.step(course / 1000f / 1000f / 1000f, 8, 8);

						//�ĕ`��
						repaint();	
					}
					
					//���Ԃ̍X�V
					pt = ct;
					
					Thread.sleep(1000 / FPS);
					
				}				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
		}	  	
	}
	
	//�R���X�g���N�^
	public double_rockman() {
		  			
	    	    
	}
  
	
	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;		
		
		// ��]��߂�
		AffineTransform ati = new AffineTransform();
		g2.setTransform(ati);
	
		//�w�i�̕`��
		Rectangle backrect = new Rectangle();
		backrect.setRect(0,0,getSize().width,getSize().height);
		g2.setColor(Color.WHITE);
		g2.fill(backrect);
		
		if(gWorld==null)return;
		
		//�{��
		final float scale = 10;
			
		for (Body body = gWorld.getBodyList(); body != null; body = body.getNext()) {
			try {
				
				Vec2 position = body.getPosition();
				Rect obj = (Rect)body.getUserData();

				// �I�u�W�F�N�g�̍�����W(�s�N�Z��)
				int oxb = (int)((position.x*scale - obj._width*scale / 2.0f));
				int oyb = (int)((position.y*scale - obj._height*scale / 2.0f));

				// �I�u�W�F�N�g�̃T�C�Y(�s�N�Z��)
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
		
		// jbox2d�̏�����//////////////////////
		Vec2 gravity = new Vec2(0, 9.8f);
		gWorld = new World(gravity);

		// �n�ʂ̒�`///////////////////////////
		BodyDef bd = new BodyDef();
		bd.position.set(64,60);
		bd.angle = (float)Math.PI / 180 * 15;
		
		//createBody�������i�K��world�ɓo�^�����
		Body body = gWorld.createBody(bd);

		PolygonShape ps = new PolygonShape();
		float w = 40f;
		float h = 5f;
		ps.setAsBox(w / 2, h / 2);

		body.createFixture(ps, 0f);
		body.setUserData(new Rect(w, h));
		
		// �`��X���b�h�J�n///////////////////////
		new Thread(new RockPhysics()).start();				
	}
	
	public static void main(String[] args) {

		JFrame f = new JFrame();
		f.setTitle("�_�u�����b�N�}��");
		f.setSize(1280,720);
		f.setLocation(200, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		//�p�l���̐ݒ�
		double_rockman panel = new double_rockman();		
		panel.init();
		
		f.add(panel);
		
		f.setVisible(true);
		
		
				
	}
  
}