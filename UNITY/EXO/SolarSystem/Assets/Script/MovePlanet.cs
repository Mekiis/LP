using UnityEngine;
using System.Collections;

public class MovePlanet : MonoBehaviour {

	public float speed = 1.0f;
	public float speedParent = 1.0f;
	public Transform parent = null;

	private float desiredDistance = 0.0f;

	// Use this for initialization
	void Start () {
		if(parent)
			desiredDistance = Vector3.Distance (parent.position, transform.position);
	}
	
	// Update is called once per frame
	void Update () {
		transform.Rotate(new Vector3(0, speed * Time.deltaTime, 0));
		if (parent) {
			transform.RotateAround(parent.position, parent.up, speedParent * Time.deltaTime);
			
			float currentDistance = Vector3.Distance (parent.position, transform.position);
			Vector3 towardTarget = transform.position - parent.position;
			transform.position += (desiredDistance - currentDistance) * towardTarget.normalized;
		}
	}
}
