package com.example.fitnesstracker.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesstracker.data.model.User;
import com.example.fitnesstracker.data.model.UserOnboarding;
import com.example.fitnesstracker.data.repository.UserRepository;

public class OnboardingViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
    private final UserRepository repository = new UserRepository();

    public void submitOnboarding(String experience, String frequency, String runningGoal, String trainingEvent) {
        UserOnboarding onboarding = new UserOnboarding(experience, frequency, runningGoal, trainingEvent);
        repository.saveOnboarding(onboarding, task -> {
            isSaved.setValue(task.isSuccessful());
        });
    }

    public void submitUserProfile(String userName, String userId, String email, String gender, double height, double weight) {
        User profile = new User(userName, userId, email, gender, height, weight);
        repository.saveUserProfile(profile, task -> {
            isSaved.setValue(task.isSuccessful());
        });
    }

    public MutableLiveData<Boolean> getIsSaved() {
        return isSaved;
    }
}
