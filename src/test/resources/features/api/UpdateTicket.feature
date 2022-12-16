#language:ru

  @test
  Функционал: Тестирование функционала сервиса Helpdesk

    Сценарий: Негативная проверка: перевод статуса тикета из "Закрыт" в "Открыт".

      * сгенерировать переменные
        | queue           | 1                |
        | due_date        | 2022-12-05       |
        | assigned_to     | admin            |
        | status          | 4                |
        | title           | EEEEEE           |
        | description     | EEEEEE           |
        | priority        | 2                |
        | submitter_email | EEEE@mail.ru     |

#      создать тикет
      * создать запрос
        | method | path         | body              |
        | POST   | api/tickets | createTicket.json |

      * добавить header
        | Accept       | application/json |
        | Content-Type | application/json |

      * отправить запрос

      * статус код 201

      * извлечь данные
        | id | $.id |

      * сравнить значения
        | ${id} | != | null |



#      авторизация
      * создать запрос
        | method | path       | body           |
        | POST   | api/login  | login.json     |

      * добавить header
        | Accept       | application/json |
        | Content-Type | application/json |

      * отправить запрос

      * статус код 200
      * извлечь данные
        | token | $.token |


#      перевод из статуса закрыт в открыт
        * сгенерировать переменные
        | status | 1 |

      * создать запрос
        | method | path               | body              |
        | PUT    | api/tickets/${id} | createTicket.json |

      * добавить header
        | Content-Type  | application/json |
        | Authorization | Token ${token}   |

      * отправить запрос
      * статус код 422
#      * статус код 200

      * извлечь данные
        | foundId   | $.id     |
        | newStatus | $.status |
      * сравнить значения
        | ${foundId}   | == | ${id} |
        | ${newStatus} | == | 4     |