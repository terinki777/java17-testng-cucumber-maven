#language:ru

  @test
  Функционал: Тестирование сервиса Helpdesk

    Сценарий: Создание тикета с высоким приоритетом

      * сгенерировать переменные
        | status          | 1                 |
        | priority        | 2                 |
        | title           | RRRRRRRRRR        |
        | submitter_email | EEEEEEE@mail.ru   |
        | assigned_to     | admin             |
        | due_date        | 2022-12-31        |
        | queue           | 1                 |

      * создать запрос
        | method | path        | body              |
        | POST   | api/tickets | createTicket.json |

      * добавить header
        | Content-Type | application/json |

      * отправить запрос

      * статус код 201

      * извлечь данные
        | id | $.id |

      И сравнить значения
        | ${id} | != | null |


    # токен
      * создать запрос
        | method | path      | body       |
        | POST   | api/login | login.json |

      * добавить header
        | Content-Type | application/json |

      * отправить запрос
      * статус код 200

      * извлечь данные
        | token | $.token |



#      сравнение данных
      * создать запрос
        | method | path              |
        | GET    | api/tickets/${id} |

      * добавить header
        | Authorization | Token ${token} |

      * отправить запрос
      * статус код 200

      * извлечь данные
        | foundId       | $.id              |
        | foundTitle    | $.title           |
        | foundStatus   | $.status          |
        | foundPriority | $.priority        |
        | foundQueue    | $.queue           |
        | foundEmail    | $.submitter_email |

      * сравнить значения
        | ${id}              | == | ${foundId}       |
        | ${title}           | == | ${foundTitle}    |
        | ${status}          | == | ${foundStatus}   |
        | ${priority}        | == | ${foundPriority} |
        | ${queue}           | == | ${foundQueue}    |
        | ${submitter_email} | == | ${foundEmail}    |

