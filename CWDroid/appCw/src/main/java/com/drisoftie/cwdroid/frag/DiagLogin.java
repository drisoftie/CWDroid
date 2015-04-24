/*
 * Copyright [2015] [Alexander Dridiger - drisoftie@gmail.com]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package com.drisoftie.cwdroid.frag;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.drisoftie.cwdroid.R;
import com.drisoftie.frags.DialogResultState;
import com.drisoftie.frags.comp.BaseDiagResult;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Alexander Dridiger
 */
public class DiagLogin extends BaseDiagResult<String[]> {

    public DiagLogin() {
    }

    @Override
    public void createDialogComponents(View dialogView) {
        ((EditText) dialogView.findViewById(R.id.edt_diag_username)).setText(getArguments().getString(getString(
                R.string.bundl_diag_input_name)));
        ((EditText) dialogView.findViewById(R.id.edt_diag_password)).setText(getArguments().getString(getString(
                R.string.bundl_diag_input_name)));
    }

    @Override
    public void buttonPositivePressed() {
        EditText username = (EditText) getDialog().findViewById(R.id.edt_diag_username);
        EditText password = (EditText) getDialog().findViewById(R.id.edt_diag_password);
        if (StringUtils.isNoneEmpty(username.getText(), password.getText())) {
            getResultListener().onResultReady(new String[]{username.getText().toString(), password.getText().toString()});
            dismiss();
        } else {
            if (StringUtils.isEmpty(username.getText())) {
                username.setError(getString(R.string.err_no_name));
            }
            if (StringUtils.isEmpty(password.getText())) {
                password.setError(getString(R.string.err_no_password));
            }
        }
    }

    @Override
    public void buttonNegativePressed() {
        getResultListener().onResultReady(null);
        dismiss();
    }

    @Override
    public void dismissDialog(DialogInterface dialog) {
        if (DialogResultState.CANCELED.equals(getResultState())) {
            getResultListener().onResultReady(null);
        }
    }
}
