package com.example.richardmacias.cs6460.data.firebase

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.richardmacias.cs6460.data.Repository
import com.example.richardmacias.cs6460.features.MainMeetList.models.MeetCard
import com.google.firebase.database.*

class OnlineDatabase{

    private var mDatabase: DatabaseReference? = null
    private var mMeetsReference: DatabaseReference? = null
    private var meetsLive: MutableLiveData<Array<MeetCard>>

    constructor(){
        meetsLive = MutableLiveData()
    }

    fun initDatabase() {
        setReference()
        val meets: MutableList<MeetCard> = mutableListOf(
                MeetCard("Zoo After school", "After school   Outdoors",
        "We're off to the Zoo after school. Anyone is welcome to join!","stuff","stuff",4),
                MeetCard("We're playing chess at lunch!", "Lunch Chess Friends",
                "Anyone can sit with us. No experience needed.","","",4)
        )
        meets.forEach {
            val key = mDatabase!!.child("meets").push().key
            it.onlineId = key
            mDatabase!!.child("meets").child(key).setValue(it)
        }

    }

    fun updateMeet(meetCard: MeetCard){
        mDatabase!!.child("meets").child(meetCard.onlineId).setValue(meetCard)
    }

      fun getMeets(listener: Repository.listListener){
        val meetListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               val meets:List<MeetCard> =  dataSnapshot.children.mapNotNull { it.getValue<MeetCard>(MeetCard::class.java) }
                Log.d("richie",meets.size.toString())
                listener.onDataLoaded(meets)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase!!.child("meets").addListenerForSingleValueEvent(meetListener)
    }

    fun getMeet(listener: Repository.itemListener,id:String){
        val meetListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               val meet:MeetCard? = dataSnapshot.child(id).getValue(MeetCard::class.java)
                Log.d("richie",meet!!.title)
                listener.onDataLoaded(meet)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase!!.child("meet").addListenerForSingleValueEvent(meetListener)
    }



    private fun setReference(){
        mDatabase = FirebaseDatabase.getInstance().reference
        mMeetsReference = FirebaseDatabase.getInstance().getReference("meets")
    }
}