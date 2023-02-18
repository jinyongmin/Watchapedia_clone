package com.watcha.watchapedia.controller;

import com.watcha.watchapedia.ifs.CrudInterface;
import com.watcha.watchapedia.model.network.Header;
import org.springframework.stereotype.Component;

    @Component
    public abstract class CrudController<Req, Res, Entity> implements CrudInterface<Req, Res> {
        @Override
        public Header<Res> create(Header<Req> request) {
            return null;
        }

        @Override
        public Header<Res> read(Long id) {
            return null;
        }

        @Override
        public Header<Res> update(Header<Req> request) {
            return null;
        }

        @Override
        public Header<Res> delete(Long id) {
            return null;
        }
    }
