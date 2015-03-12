using UnityEngine;
using System.Collections;

internal class Ball : MonoBehaviour {
	public GameManager manager = null;
	public int points = 1;

	// Use this for initialization
	void Start () {
	}
	
	// Update is called once per frame
	void Update () {

	}

	void OnTriggerEnter(Collider other) {
		if (manager != null)
			manager.points += points;
		Destroy(gameObject);
	}

	internal void Move(){
		GetComponent<Rigidbody>().AddForce(new Vector3(Random.Range(-3, 3), 0, Random.Range(-3, 3)), ForceMode.Impulse);
	}
}
