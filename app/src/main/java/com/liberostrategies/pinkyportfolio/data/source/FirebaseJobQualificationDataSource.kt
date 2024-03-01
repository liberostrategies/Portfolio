package com.liberostrategies.pinkyportfolio.data.source

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.liberostrategies.pinkyportfolio.data.model.JobQualificationDataModel

class FirebaseJobQualificationDataSource : IJobQualificationDataSource {
    private var mCollectionJobQualifications: CollectionReference

    private var mListQualifications = mutableListOf<JobQualificationDataModel>()
    private var initialQualificationsSize = 0

    init {
        /****************************************************************************************
         *  Access Firebase DB.
         ***************************************************************************************/
        val db = Firebase.firestore
        mCollectionJobQualifications = db.collection("jobqualifications")
    }

    override suspend fun getListQualifications(): MutableList<JobQualificationDataModel> {
        return mListQualifications
    }

    /**
     * Read [category] and [jobQualification] from ViewModel, which gets data from Firebase DB.
     */
    override suspend fun readQualification(category: String, jobQualification: String) {
        mListQualifications.add(JobQualificationDataModel(category, jobQualification))
    }

    /**
     * Read [category] and [jobQualification] from ViewModel, which gets data from Firebase DB.
     */
    override fun readInitialQualificationsSize(size: Int) {
        initialQualificationsSize = size
    }

    override fun matchQualificationsWithSkills(selectedJobQualificationsSize: Int): Int {
        return (selectedJobQualificationsSize.toDouble() / initialQualificationsSize * 100).toInt()
    }

    /**
     * NOTE: Couldn't get this to work to read directly from Firebase DB. Moved to Compose UI layer.
     */
//    override suspend fun readQualifications(category: String): MutableList<JobQualificationDataModel> {
//        Logger.d(this.javaClass.simpleName) { "Category[$category] readQualifications for category [$category]" }
//        val docCategory = mCollectionJobQualifications.document(category)
//        val listQualifications = mutableListOf<JobQualificationDataModel>()
//        docCategory.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Logger.d(this.javaClass.simpleName) { "$category qualifications: ${document.data}" }
//                    var i = 0
//                    while (document.data?.get("$i") != null) {
//                        val q = document.data?.get("$i").toString()
//                        listQualifications.add(JobQualificationDataModel(category, q))
//                        i++
//                    }
//                    Logger.d(this.javaClass.simpleName) { "Qualifications 1[$listQualifications]" }
//                    mListQualifications = listQualifications
//                } else {
//                    Logger.d(this.javaClass.simpleName) { "No such document" }
//                }
//                Logger.d(this.javaClass.simpleName) { "Qualifications 2[$listQualifications]" }
//            }
//            .addOnFailureListener { exception ->
//                Logger.d(this.javaClass.simpleName) { "get failed with $exception" }
//                throw JobQualificationsDoNotExistException(exception.toString())
//            }
//        Logger.d(this.javaClass.simpleName) { "Qualifications 3[$listQualifications]" }
//        return listQualifications
//    }
}