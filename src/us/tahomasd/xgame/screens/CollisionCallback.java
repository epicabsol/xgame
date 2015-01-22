package us.tahomasd.xgame.screens;

import us.tahomasd.xgame.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionCallback implements ContactListener
{

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		XGameCore.GameScreen.OnCollisionStarted(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		XGameCore.GameScreen.OnCollisionEnded(contact.getFixtureA().getBody(), contact.getFixtureB().getBody());
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
