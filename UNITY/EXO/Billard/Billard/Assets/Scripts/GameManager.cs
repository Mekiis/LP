using UnityEngine;
using System.Collections;

internal class GameManager : MonoBehaviour {
	public Camera cam = null;
	public float coefSpeed = 0.00001f;

	internal int points = 0;

	internal GameObject ball = null;
	internal Vector3 origin = new Vector3 (0, 0, 0);
	
	// Use this for initialization
	void Start () {
		points = 0;
	}
	
	// Update is called once per frame
	void Update () {
		if (Input.GetMouseButtonDown(0)) {
			if(ball == null){
				Ray ray = cam.ScreenPointToRay(Input.mousePosition);
				RaycastHit hit;
				
				if (Physics.Raycast(ray, out hit, 10000)){
					if(hit.transform.gameObject.GetComponent<Ball>() != null){
						origin = Input.mousePosition;
						ball = hit.transform.gameObject;
					}
				}
			}
			if(ball != null)
				Debug.DrawLine(origin, Input.mousePosition, Color.red);
		}

		if (Input.GetMouseButtonUp(0)) {
			if(ball != null){
				ball.GetComponent<Rigidbody>().AddForce(new Vector3((Input.mousePosition.x - origin.x) / 10f, 0, (Input.mousePosition.y - origin.y) / 10f), ForceMode.Impulse);
				ball = null;
			}

		}
	}

	void OnGUI(){
		GUI.Box(new Rect(0, 0, 100, 20), "Points : "+points);
	}
}
