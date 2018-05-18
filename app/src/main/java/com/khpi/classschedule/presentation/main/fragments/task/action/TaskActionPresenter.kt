package com.khpi.classschedule.presentation.main.fragments.task.action

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.TaskRepository
import com.khpi.classschedule.data.models.*
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TaskActionPresenter : BasePresenter<TaskActionView>() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var scheduleRepository: ScheduleRepository

    init {
        injector().inject(this)
    }

    private var prefix: String? = null
    private var infoTypes: MutableList<BaseModel>? = null
    private var currentTask: Task? = null

    private var selectedGroup: String? = null
    private var selectedSubject: String? = null
    private var selectedType = CoupleType.LECTURE
    private var notificationTime: Long? = null
    private var description = ""

    override fun onViewLoaded() {
        prefix = getPrefixByType(ScheduleType.GROUP)
        prefix?.let { infoTypes = scheduleRepository.getScheduleInfoByTypes(it) }
        checkAllFieldAreFilled()
        viewState.configureView()
    }

    fun loadInfoOfExistingTask(task: Task?) {
        currentTask = task ?: return
        selectedGroup = task.group
        selectedSubject = task.subject
        selectedType = task.type
        notificationTime = task.notificationTime
        description = task.description

        viewState.updateTaskInfo(task)
        viewState.setConfirmButtonEnabled(true)
    }

    fun setGroupAndSubjectInfo(group: String?, subject: String?, type: String?) {
        selectedGroup = group ?: return
        selectedSubject = subject ?: return
        val unwrappedType = type ?: return
        selectedType = CoupleType.values().first { it.title == unwrappedType }

        selectedGroup?.let { unwrappedGroup ->
            selectedSubject?.let { unwrappedSubject ->
                viewState.setGroupAndSubject(unwrappedGroup, unwrappedSubject, selectedType)
            }
        }

    }

    fun prepareToShowGroup() {
        val info = infoTypes ?: return
        val groupNames = info.mapNotNull { it.title }
        viewState.showPopupGroup(groupNames)
    }

    fun prepareToShowSubject() {

        val group = selectedGroup ?: run {
            viewState.showWarningEmptyGroup()
            return
        }

        val id = infoTypes?.find { it.title == group }?.id ?: return
        val schedulePair = prefix?.let { scheduleRepository.getSchedule(it, id) }

        val scheduleForFirstWeek = schedulePair?.first ?: return
        val scheduleForSecondWeek = schedulePair.second

        fun addNotContainsSubjects(subjects: MutableList<String>, schedulePerDay: List<ScheduleItem>) : MutableList<String> {

            schedulePerDay.forEach { item ->
                if (item.name != null && !subjects.contains(item.name)) {
                    subjects.add(item.name)
                }
            }

            return subjects
        }

        var subjects = mutableListOf<String>()

        subjects = addNotContainsSubjects(subjects, scheduleForFirstWeek.monday)
        subjects = addNotContainsSubjects(subjects, scheduleForFirstWeek.tuesday)
        subjects = addNotContainsSubjects(subjects, scheduleForFirstWeek.wednesday)
        subjects = addNotContainsSubjects(subjects, scheduleForFirstWeek.thursday)
        subjects = addNotContainsSubjects(subjects, scheduleForFirstWeek.friday)

        subjects = addNotContainsSubjects(subjects, scheduleForSecondWeek.monday)
        subjects = addNotContainsSubjects(subjects, scheduleForSecondWeek.tuesday)
        subjects = addNotContainsSubjects(subjects, scheduleForSecondWeek.wednesday)
        subjects = addNotContainsSubjects(subjects, scheduleForSecondWeek.thursday)
        subjects = addNotContainsSubjects(subjects, scheduleForSecondWeek.friday)

        viewState.showPopupSubject(subjects)

    }

    fun prepareToShowDatePicker() {
        currentTask?.let { viewState.showDatePicker(it.notificationTime) }
                ?: viewState.showDatePicker(null)
    }

    fun onGroupSelected(selectedGroup: String) {
        this.selectedGroup = selectedGroup
        checkAllFieldAreFilled()
    }

    fun onSubjectSelected(selectedSubject: String) {
        this.selectedSubject = selectedSubject
        checkAllFieldAreFilled()
    }

    fun onTypeSelected(item: CoupleType, position: Int) {
        this.selectedType = item
        viewState.highlightSelectedType(position)
        checkAllFieldAreFilled()
    }

    fun setNotificationTime(notificationTime: Long) {
        this.notificationTime = notificationTime
        checkAllFieldAreFilled()
    }

    fun onDescriptionChanged(description: String) {
        this.description = description
        checkAllFieldAreFilled()
    }

    private fun checkAllFieldAreFilled() {
        if (selectedGroup != null && selectedSubject != null  &&
                notificationTime != null && description.isNotEmpty()) {
            viewState.setConfirmButtonEnabled(true)
        } else {
            viewState.setConfirmButtonEnabled(false)
        }
    }

    fun saveTask() {

        val group = selectedGroup ?: return
        val subject = selectedSubject ?: return
        val notificationTime = notificationTime ?: return

        currentTask?.let {
            val task = Task(it.id, group, subject, selectedType, notificationTime, description)
            taskRepository.saveTask(task, true)
            viewState.showMessage("Завдання було оновлено")
            viewState.configureNotification(task)
        } ?: run {
            val id = taskRepository.getLastTaskId(Constants.GROUP_PREFIX) + 1
            val task = Task(id, group, subject, selectedType, notificationTime, description)
            taskRepository.saveTask(task, false)
            viewState.showMessage("Завдання було створено")
            viewState.configureNotification(task)
        }

        viewState.closeScreen()
    }
}