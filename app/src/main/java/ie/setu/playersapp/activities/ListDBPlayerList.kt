/*package ie.setu.mobileassignment.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ie.setu.mobileassignment.R
import ie.setu.mobileassignment.adapters.ExperienceAdapter
import ie.setu.mobileassignment.adapters.ExperienceListener
import ie.setu.mobileassignment.main.MainApp
import ie.setu.mobileassignment.models.PlayerModel
import ie.setu.mobileassignment.models.User
import timber.log.Timber.i
import java.util.*

var hasLoggedIn = false

class ListDBPlayerList : AppCompatActivity(), ExperienceListener {

    // lateinit used to overrule null safety checks
    lateinit var app: MainApp
    private var signedInUser: User? = null
    private lateinit var binding: ListDBPlayerList
    private lateinit var firestoreDb: FirebaseFirestore

    @SuppressLint("ThrowableNotAtBeginning", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListDBPlayerList.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.email as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                i("Signed in user: ${signedInUser?.username}")

                // Here we are only getting the experiences where the experience has been created by the logged-in user
                val experiencesReference = firestoreDb
                    .collection("experiences")
                    .whereEqualTo("user.username", signedInUser?.username)
                experiencesReference.addSnapshotListener { snapshot, exception ->
                    if (exception != null || snapshot == null) {
                        i("Exception when querying posts: %s", exception)
                        return@addSnapshotListener
                    }
                    val listOfExperiences = snapshot.toObjects(PlayerModel::class.java)
                    app.experiences.updateExperiencesToShow(listOfExperiences)
                    for (experience in listOfExperiences) {
                        i("Experience: $experience")
                    }

                    // Cheap way of fixing a bug where the recyclerview does not get updated the first time opening the app e
                    if (!hasLoggedIn) {
                        hasLoggedIn = true
                        recreate()
                    }
                }
            }
            .addOnFailureListener{ exception ->
                i("Failure fetching signed in user: $exception")
            }


        // layoutManager used for positioning items
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        // Adapter used to bind data to Views such as the RecyclerView
        binding.recyclerView.adapter = ExperienceAdapter(app.experiences.findAll(), this)

        binding.fabBtn.setOnClickListener {
            val addButtonText = R.string.button_openAddActivity
            Toast.makeText(applicationContext, addButtonText, Toast.LENGTH_SHORT).show()
            // Logging info shown in Logcat
            i("Add button pressed...")
            // Calling function to open the main activity.
            val intent = Intent(this, AddBucketListActivity::class.java)
            // Launches the activity and awaits for the activity to complete (Get an 'OK' result), then updates the UI
            getResult.launch(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        // The below is for opening the about us activity from the 3 dots menu item
        val aboutUsItem = menu?.findItem(R.id.about)
        aboutUsItem?.setOnMenuItemClickListener {
            // Logging info shown in Logcat
            i("Opening About Us activity...")
            // Calling function to open the about us activity.
            val intent = Intent(this, AboutBucketListActivity::class.java)
            startActivity(intent)
            true
        }

        // The below is for opening the profile activity from the 3 dots menu item
        val profileItem = menu?.findItem(R.id.profile)
        profileItem?.setOnMenuItemClickListener {
            // Logging info shown in Logcat
            i("Opening profile activity...")
            // Calling function to open the profile activity.
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            true
        }

        /*Menu with SearchView reference: https://www.youtube.com/watch?v=M3UDh9mwBd8
    * Since I'm using a RecyclerView, I had to create a filter function in ExperienceAdapter as it doesn't have one built in.
    * Great reference to achieve it with RecyclerView: https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview */
        // assigning search button to menuItem
        val menuItem = menu?.findItem(R.id.action_search)
        // getting the SearchView after clicking on the menuItem
        val searchView = menuItem?.actionView as SearchView
        searchView.queryHint = "Search for experience"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // For when user submits the query by hitting enter
            override fun onQueryTextSubmit(query: String): Boolean {
                val adapter = binding.recyclerView.adapter as ExperienceAdapter
                adapter.filter(query)
                return false
            }
            // For when user is typing query, this way it keeps searching in real-time
            override fun onQueryTextChange(newText: String): Boolean {
                val adapter = binding.recyclerView.adapter as ExperienceAdapter
                adapter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }





    override fun onExperienceClick(experience: PlayerModel) {
        val launcherIntent = Intent(this, AddBucketListActivity::class.java)
        launcherIntent.putExtra("experience_edit", experience)
        getResult.launch(launcherIntent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyDataSetChanged()
                recreate() /* Recreating the list activity when getting a RESULT_OK back.
                 Without it, the items in the list activity do not get refreshed when we add or delete
                 a new item. This is a way of refreshing the activity.
                 Reference: https://stackoverflow.com/questions/3053761/reload-activity-in-android */
            }
        }
}
*/