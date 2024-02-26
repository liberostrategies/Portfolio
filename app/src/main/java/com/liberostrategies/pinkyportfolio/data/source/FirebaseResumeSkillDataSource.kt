package com.liberostrategies.pinkyportfolio.data.source

import co.touchlab.kermit.Logger
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseResumeSkillDataSource : IResumeSkillDataSource {
    /****************************************************************************************
     *  Access Firebase DB.
     ***************************************************************************************/
    private var mCollectionResume: CollectionReference

    var skills = StringBuilder()

    override fun getSkills() : String {
        return skills.toString()
    }
    init {
        val db = Firebase.firestore
        mCollectionResume = db.collection("resume")
    }

    override fun readSkills(companyIndex: Int, jobIndex: Int): String {
        Logger.d(this.javaClass.simpleName) { "readSkills(companyIndex=$companyIndex, jobIndex=$jobIndex)" }
        val docCompanies = mCollectionResume.document("companies")
        val collectionCompany = docCompanies.collection("company$companyIndex")
        val docJob = collectionCompany.document("job$jobIndex")
        docJob.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Logger.d(this.javaClass.simpleName) { "techSkills: ${document.data?.get("tech")}" }
                    if (document.data?.get("tech") != null) {
                        val skill = document.data?.get("tech").toString()
                        skills.clear().append(skill)
                    }
                    Logger.d(this.javaClass.simpleName) { "Skills 1[$skills]" }
                } else {
                    Logger.d(this.javaClass.simpleName) { "No such document" }
                }
                Logger.d(this.javaClass.simpleName) { "Skills 2[$skills]" }
            }
            .addOnFailureListener { exception ->
                Logger.d(this.javaClass.simpleName) { "get failed with $exception" }
                throw JobQualificationsDoNotExistException(exception.toString())
            }
        Logger.d(this.javaClass.simpleName) { "Skills 3[$skills]" }
        return skills.removeSuffix(".").toString()
    }
}