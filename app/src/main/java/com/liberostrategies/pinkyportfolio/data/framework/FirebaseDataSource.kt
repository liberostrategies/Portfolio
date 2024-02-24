package com.liberostrategies.pinkyportfolio.data.framework

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel
import com.liberostrategies.pinkyportfolio.data.source.IJobQualificationDataSource
import co.touchlab.kermit.Logger
import com.liberostrategies.pinkyportfolio.data.source.JobQualificationsDoNotExistException

class FirebaseDataSource : IJobQualificationDataSource {
    /****************************************************************************************
     *  Access Firebase DB.
     ***************************************************************************************/
    private var collectionJobQualifications: CollectionReference

    init {
        val db = Firebase.firestore
        collectionJobQualifications = db.collection("jobqualifications")
    }

    override suspend fun createQualification(
        category: String,
        qualification: String
    ): JobQualificationDataModel {
        TODO("Not yet implemented")
    }

    override suspend fun readQualifications(category: String): List<JobQualificationDataModel> {
        Logger.d(this.javaClass.simpleName) { "Category[$category] readQualifications for category [$category]" }
        val docCategory = collectionJobQualifications.document(category)
        val listQualifications = mutableListOf<JobQualificationDataModel>()
        docCategory.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Logger.d(this.javaClass.simpleName) { "$category qualifications: ${document.data}" }
                    var i: Int = 0
                    var qual = ""
                    while (document.data?.get("$i") != null) {
                        qual = document.data?.get("$i").toString()
                        listQualifications.add(JobQualificationDataModel(category, qual))
                        i++
                    }
                } else {
                    Logger.d(this.javaClass.simpleName) { "No such document" }
                }
            }
            .addOnFailureListener { exception ->
                Logger.d(this.javaClass.name) { "get failed with $exception" }
                throw JobQualificationsDoNotExistException(exception.toString())
            }
        Logger.d(this.javaClass.name) { "Category[$category] qualifications[$listQualifications]" }
        return listQualifications
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