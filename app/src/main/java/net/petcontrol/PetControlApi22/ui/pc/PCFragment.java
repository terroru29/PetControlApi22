package net.petcontrol.PetControlApi22.ui.pc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.petcontrol.PetControlApi22.databinding.FragmentPcBinding;

public class PCFragment extends Fragment {

    private FragmentPcBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PCViewModel PCViewModel =
                new ViewModelProvider(this).get(PCViewModel.class);

        binding = FragmentPcBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPc;
        PCViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}