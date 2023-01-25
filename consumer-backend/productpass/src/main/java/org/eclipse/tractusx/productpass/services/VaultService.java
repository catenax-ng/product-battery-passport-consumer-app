/*********************************************************************************
 *
 * Catena-X - Product Passport Consumer Backend
 *
 * Copyright (c) 2022, 2023 BASF SE, BMW AG, Henkel AG & Co. KGaA
 * Copyright (c) 2022, 2023 Contributors to the CatenaX (ng) GitHub Organisation.
 *
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the
 * License for the specific language govern in permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/

package org.eclipse.tractusx.productpass.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.tractusx.productpass.exceptions.ServiceException;
import org.eclipse.tractusx.productpass.exceptions.ServiceInitializationException;
import org.eclipse.tractusx.productpass.models.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VaultService extends BaseService {
    public static final ConfigUtil configuration = new ConfigUtil();
    @Autowired
    private VaultTemplate vaultTemplate;

    public VaultService() throws ServiceInitializationException {
        this.checkEmptyVariables();
    }

    public Object mapSecret(String secretPath, Class<?> ClassType) {
        try {
            VaultResponse vaultResponse = vaultTemplate.read(secretPath);
            if(vaultResponse == null){
                throw new ServiceException(this.getClass().getName()+"."+"getSecret",
                        "It was not possible to get secret from vault. Vault Response is null!");
            }
            Object data = vaultResponse.getData();
            if(data == null){
                throw new ServiceException(this.getClass().getName()+"."+"getSecret",
                        "It was not possible to get secret from vault. Data is null!");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(data, ClassType);
        }catch (Exception e){
            throw new ServiceException(this.getClass().getName()+"."+"mapSecret",
                    e,
                    "It was not possible to map secret from vault.");
        }
    }
    public Object getSecret(String secretPath) {
        try {
            VaultResponse vaultResponse = vaultTemplate.read(secretPath);
            if(vaultResponse == null){
                throw new ServiceException(this.getClass().getName()+"."+"getSecret",
                        "It was not possible to get secret from vault. Vault Response is null!");
            }
            Map<?, ?> data = vaultResponse.getData();
            if(data == null){
                throw new ServiceException(this.getClass().getName()+"."+"getSecret",
                        "It was not possible to get secret from vault. Data is null!");
            }
            return data.get("data");
        }catch (Exception e){
            throw new ServiceException(this.getClass().getName()+"."+"getSecret",
                    e,
                    "It was not possible to get secret from vault.");
        }
    }

    public Object getLocalSecret(String localSecretPath) {
        try {
            String secret = null;
            String filePath = VaultUtil.createLocalVaultFile(true);
            Map<String, Object> content = YamlUtil.readFile(filePath);
            try {
                secret = (String) JsonUtil.getValue(content,localSecretPath, ".",null);
            }catch (Exception e){
                LogUtil.printException(e, "["+this.getClass().getName()+"."+"getLocalSecret] " + "There was a error while searching the secret ["+localSecretPath+"] in file!");
                //throw new ServiceException(this.getClass().getName()+"."+"getLocalSecret", e, "There was a error while searching the secret ["+localSecretPath+"] in file!");
            }
            if(secret == null){
                LogUtil.printError("["+this.getClass().getName()+"."+"getLocalSecret] " + "Secret ["+localSecretPath+"] not found in file!");
                //throw new ServiceException(this.getClass().getName()+"."+"getLocalSecret", "Secret ["+localSecretPath+"] not found in file!");
            }
            return secret;
        }catch (Exception e){
            throw new ServiceException(this.getClass().getName()+"."+"getLocalSecret",
                    e,
                    "It was not possible to get secret from file.");
        }
    }


    @Override
    public List<String> getEmptyVariables() {
        return new ArrayList<>();
    }
}