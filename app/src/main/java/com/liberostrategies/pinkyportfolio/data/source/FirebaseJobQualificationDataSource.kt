package com.liberostrategies.pinkyportfolio.data.source

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel
import co.touchlab.kermit.Logger

class FirebaseJobQualificationDataSource : IJobQualificationDataSource {
    /****************************************************************************************
     *  Access Firebase DB.
     ***************************************************************************************/
    private var mCollectionJobQualifications: CollectionReference

    init {
        val db = Firebase.firestore
        mCollectionJobQualifications = db.collection("jobqualifications")
    }


    override suspend fun createQualification(
        category: String,
        qualification: String
    ): JobQualificationDataModel {
        TODO("Not yet implemented")
    }

    var mListQualifications = mutableListOf<JobQualificationDataModel>()

    override suspend fun getListQualifications(): MutableList<JobQualificationDataModel> {
        return mListQualifications
    }

    override suspend fun readQualifications(category: String): MutableList<JobQualificationDataModel> {
        Logger.d(this.javaClass.simpleName) { "Category[$category] readQualifications for category [$category]" }
        val docCategory = mCollectionJobQualifications.document(category)
        val listQualifications = mutableListOf<JobQualificationDataModel>()
        docCategory.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Logger.d(this.javaClass.simpleName) { "$category qualifications: ${document.data}" }
                    var i = 0
                    while (document.data?.get("$i") != null) {
                        val q = document.data?.get("$i").toString()
                        listQualifications.add(JobQualificationDataModel(category, q))
                        i++
                    }
                    Logger.d(this.javaClass.simpleName) { "Qualifications 1[$listQualifications]" }
                    mListQualifications = listQualifications
                } else {
                    Logger.d(this.javaClass.simpleName) { "No such document" }
                }
                Logger.d(this.javaClass.simpleName) { "Qualifications 2[$listQualifications]" }
            }
            .addOnFailureListener { exception ->
                Logger.d(this.javaClass.simpleName) { "get failed with $exception" }
                throw JobQualificationsDoNotExistException(exception.toString())
            }
        Logger.d(this.javaClass.simpleName) { "Qualifications 3[$listQualifications]" }
        return listQualifications
    }

    override suspend fun readAllQualifications(): MutableList<JobQualificationDataModel> {
        val list = mutableListOf<JobQualificationDataModel>()
//        list.add(readQualifications("certifications"))
        return list
    }

    override suspend fun readAllQualificationsAsJson(): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateQualification(
        category: String,
        qualification: String,
        newQualificationValue: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteQualification(category: String, qualification: String) {
        TODO("Not yet implemented")
    }
}