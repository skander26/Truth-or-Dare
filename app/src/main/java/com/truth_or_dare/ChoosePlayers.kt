package com.truth_or_dare
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ChoosePlayers : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var players: ArrayList<String>
    private lateinit var listView: ListView
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_players)

        listView = findViewById(R.id.listView)
        val addbtn = findViewById<Button>(R.id.addbutton)
        val startbtn = findViewById<Button>(R.id.stratButn)
        gestureDetector = GestureDetector(this, this)

        players = arrayListOf("najwa", "mostfa", "hana", "ahmed","mohamed","skander","FOLSkZYz")

        // Create an ArrayAdapter
        val adapter = ArrayAdapter(this, R.layout.activity_list_view, R.id.text2, players)

        // Set the adapter for the ListView
        listView.adapter = adapter

        addbtn.setOnClickListener {
            popupadd(adapter, players)
        }
        startbtn.setOnClickListener {
            val intent = Intent(this, GamePage::class.java)
            intent.putStringArrayListExtra("players", players)
            startActivity(intent)
        }

        listView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    private fun popupadd(adapter: ArrayAdapter<String>, players: ArrayList<String>) {
        val dialogView = layoutInflater.inflate(R.layout.popup_add_player, null)
        val editTextPlayerName = dialogView.findViewById<EditText>(R.id.editTextPlayerName)
        val buttonAddPlayer = dialogView.findViewById<Button>(R.id.buttonAddPlayer)

        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add New Player")
            .setCancelable(true)

        val dialog = builder.create()
        dialog.show()

        buttonAddPlayer.setOnClickListener {
            val playerName = editTextPlayerName.text.toString().trim()
            if (playerName.isNotEmpty()) {
                players.add(playerName)
                adapter.notifyDataSetChanged()
                editTextPlayerName.text.clear()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please enter a player name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent) {}

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 == null || e2 == null) return false

        val deltaX = e2.x - e1.x
        val deltaY = e2.y - e1.y

        // Check if swipe is from right to left and horizontal
        if (Math.abs(deltaX) > Math.abs(deltaY) && deltaX < 0) {
            val position = listView.pointToPosition(e1.x.toInt(), e1.y.toInt())
            val item = listView.adapter.getItem(position) as String
            players.remove(item)
            (listView.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            Toast.makeText(this, "Deleted: $item", Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }



}
