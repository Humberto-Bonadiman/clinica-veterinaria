package br.com.springboot.clinica.service;

import java.util.List;

public interface ServiceInterface<T, K> {

  public K create(T object);

  public K findById(Long id);

  public List<K> findAll();

  public void update(Long id, T object);
}
