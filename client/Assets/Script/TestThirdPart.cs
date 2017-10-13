﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestThirdPart : MonoBehaviour {

	public ConnectionManager connectionManager;
	public RemotePlayerController remotePlayerController;

	private ThirdPartManager thirdPartManager;

	// Use this for initialization
	void Start () 
	{
		thirdPartManager = ThirdPartManager.Instance ;

	}

	void Update()
	{
		if (Input.GetKeyDown (KeyCode.Q)) {
			thirdPartManager.SetManager (connectionManager.GetManager());
			thirdPartManager.SetRemotePlayerController (remotePlayerController);

			thirdPartManager.StartSend ();
		}
			
	}

	// Update is called once per frame
	void OnDestroy () 
	{
		thirdPartManager.StopSend ();
	}
}