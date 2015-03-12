using UnityEngine;
using System.Collections;

public class Light : MonoBehaviour {
	public float speed = 0.5f;

	private float position = 0f;
	private int sens = 1;

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		if(sens == 1){
			position += Time.deltaTime * speed;
		} else {
			position -= Time.deltaTime * speed;
		}
		transform.position = new Vector3 (transform.position.x, transform.position.y, position);

		if (position < 0) {
			sens = 1;
		} else if (position > 2) {
			sens = -1;
		}
	}
}
