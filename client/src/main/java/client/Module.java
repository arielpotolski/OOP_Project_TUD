package client;

import client.scenes.AdminAddActivityScreenCtrl;
import client.scenes.AdminInterfaceScreenCtrl;
import client.scenes.AdminRemoveActivityScreenCtrl;
import client.scenes.GlobalLeaderboardScreenCtrl;
import client.scenes.IntLeaderboardCtrl;
import client.scenes.IntermediateSceneCtrl;
import client.scenes.MainCtrl;
import client.scenes.MultiplayerPreGameCtrl;
import client.scenes.MultiplayerQuestionScreenCtrl;
import client.scenes.QuestionScreenSinglePlayerCtrl;
import client.scenes.SinglePlayerFinalScreenCtrl;
import client.scenes.SinglePlayerPreGameCtrl;
import client.scenes.SplashCtrl;

import com.google.inject.Binder;
import com.google.inject.Scopes;

public class Module implements com.google.inject.Module {
	private Binder binder;

	@Override
	public void configure(Binder binder) {
		this.binder = binder;
		this.bind(GlobalLeaderboardScreenCtrl.class);
		this.bind(QuestionScreenSinglePlayerCtrl.class);
		this.bind(MainCtrl.class);
		this.bind(SinglePlayerPreGameCtrl.class);
		this.bind(SplashCtrl.class);
		this.bind(MultiplayerPreGameCtrl.class);
		this.bind(IntermediateSceneCtrl.class);
		this.bind(SinglePlayerFinalScreenCtrl.class);
		this.bind(AdminInterfaceScreenCtrl.class);
		this.bind(AdminAddActivityScreenCtrl.class);
		this.bind(AdminRemoveActivityScreenCtrl.class);
		this.bind(MultiplayerQuestionScreenCtrl.class);
		this.bind(IntLeaderboardCtrl.class);
	}

	private void bind(Class<?> c) {
		this.binder.bind(c).in(Scopes.SINGLETON);
	}
}